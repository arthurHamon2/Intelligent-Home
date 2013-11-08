package server.dsl;
import java.util.Iterator;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

//java -jar ../../../../lib/antlr-4.0-complete.jar Expr.g 
//javac -cp ../../../../lib/antlr-4.0-complete.jar *.java
//java -cp ../../../../lib/antlr-4.0-complete.jar:.   Builder
public class Builder
{
    public Builder()
    {
    }

    public String generate(String name, String rule) throws ParserException
    {
        ExprTestListener collector = this.getCollector(name, rule);
        
        if (collector.getCondition() == null)
            throw new ParserException(rule);
        
        return this.format(name, collector);
    }

    protected ExprTestListener getCollector(String name, String rule)
    {
        CharStream input = new ANTLRInputStream(rule);
        ExprLexer lexer = new ExprLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);

        // parser generates abstract syntax tree
        ExprParser parser = new ExprParser(tokens);
        ExprParser.ExpressionContext uu = parser.expression();
        ExprTestListener collector = new ExprTestListener();
        new ParseTreeWalker().walk(collector, uu);
        return collector;
    }

    protected String format(String name, ExprTestListener collector)
    {
        String r = "";
        String condition = collector.getCondition();
        condition = condition.replace("==", " = ");
        condition = condition.replace("!=", " <> ");
        condition = condition.replace("&&", " AND ");
        condition = condition.replace("||", " OR ");
        condition = condition.replaceAll("M(\\d{1,})", "(SELECT VALUE FROM T_MEASURE WHERE IDMEASURE = $1)");
        r += "CREATE TRIGGER " + name + " AFTER UPDATE ON T_MEASURE\n";
        r += "WHEN " + condition + "\n";
        r += "BEGIN\n";

        Iterator<Long> ops = collector.getOperators().iterator();
        Iterator<Long> acts = collector.getActions().iterator();
        Long op;
        Long act;
        while (ops.hasNext() && acts.hasNext())
        {
            op = ops.next();
            act = acts.next();
            r += "  DELETE FROM T_ACTIONS_TODO WHERE OPERATORREF = " + op + " AND IDACTION = " + act + ";\n";
            r += "  INSERT INTO T_ACTIONS_TODO(IDACTION, OPERATORREF, ADDEDAT) VALUES(" + act + ", " + op + ", datetime('now'));\n";
        }

        r += "END;\n";
        return r;
    }
}
