package server.dsl;
// Generated from Expr.g by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface ExprListener extends ParseTreeListener {
	void enterExpression(ExprParser.ExpressionContext ctx);
	void exitExpression(ExprParser.ExpressionContext ctx);

	void enterActionexpr(ExprParser.ActionexprContext ctx);
	void exitActionexpr(ExprParser.ActionexprContext ctx);

	void enterCondexpr(ExprParser.CondexprContext ctx);
	void exitCondexpr(ExprParser.CondexprContext ctx);

	void enterCompared(ExprParser.ComparedContext ctx);
	void exitCompared(ExprParser.ComparedContext ctx);

	void enterCond(ExprParser.CondContext ctx);
	void exitCond(ExprParser.CondContext ctx);
}