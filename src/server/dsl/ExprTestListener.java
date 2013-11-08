package server.dsl;

import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import server.dsl.ExprParser.ComparedContext;

public class ExprTestListener  implements ExprListener
{
    @Override public void enterExpression(ExprParser.ExpressionContext ctx) { }
    @Override public void exitExpression(ExprParser.ExpressionContext ctx) { }

    @Override public void enterActionexpr(ExprParser.ActionexprContext ctx) { }
    @Override public void enterCondexpr(ExprParser.CondexprContext ctx) { }
    @Override public void enterCond(ExprParser.CondContext ctx) { }
    @Override public void exitCond(ExprParser.CondContext ctx) { }
    @Override public void enterEveryRule(ParserRuleContext ctx) { }
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    @Override public void visitTerminal(TerminalNode node) { }
    @Override public void visitErrorNode(ErrorNode node) { }
    @Override public void enterCompared(ComparedContext ctx) { }
    @Override public void exitCompared(ComparedContext ctx) { }

    private String condition;
    private List<Long> actions = new ArrayList<Long>();
    private List<Long> operators = new ArrayList<Long>();

	@Override public void exitActionexpr(ExprParser.ActionexprContext ctx)
    {
        String a = ctx.ACTION().getText();
        try
        {
            a = a.substring(1);
            String[] ms = a.split(":", 2);
            Long op = Long.parseLong(ms[0]);
            Long action = Long.parseLong(ms[1]);
            this.actions.add(action);
            this.operators.add(op);
        }
        catch(Exception e)
        {
        }
    }

	@Override public void exitCondexpr(ExprParser.CondexprContext ctx)
    {
        this.condition = ctx.getText();
    }

    public String getCondition()
    {
        return this.condition;
    }

    public List<Long> getActions()
    {
        return this.actions;
    }

    public List<Long> getOperators()
    {
        return this.operators;
    }

}
