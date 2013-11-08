package server.dsl;
// Generated from Expr.g by ANTLR 4.0
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, WS=5, OP=6, COMP=7, REAL=8, ACTION=9, 
		MEASURE=10;
	public static final String[] tokenNames = {
		"<INVALID>", "'ON'", "')'", "'DO'", "'('", "WS", "OP", "COMP", "REAL", 
		"ACTION", "MEASURE"
	};
	public static final int
		RULE_compared = 0, RULE_cond = 1, RULE_condexpr = 2, RULE_actionexpr = 3, 
		RULE_expression = 4;
	public static final String[] ruleNames = {
		"compared", "cond", "condexpr", "actionexpr", "expression"
	};

	@Override
	public String getGrammarFileName() { return "Expr.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ComparedContext extends ParserRuleContext {
		public TerminalNode REAL() { return getToken(ExprParser.REAL, 0); }
		public TerminalNode MEASURE() { return getToken(ExprParser.MEASURE, 0); }
		public ComparedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compared; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterCompared(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitCompared(this);
		}
	}

	public final ComparedContext compared() throws RecognitionException {
		ComparedContext _localctx = new ComparedContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compared);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			_la = _input.LA(1);
			if ( !(_la==REAL || _la==MEASURE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondContext extends ParserRuleContext {
		public List<ComparedContext> compared() {
			return getRuleContexts(ComparedContext.class);
		}
		public TerminalNode COMP() { return getToken(ExprParser.COMP, 0); }
		public ComparedContext compared(int i) {
			return getRuleContext(ComparedContext.class,i);
		}
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitCond(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		CondContext _localctx = new CondContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12); compared();
			setState(13); match(COMP);
			setState(14); compared();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondexprContext extends ParserRuleContext {
		public CondexprContext condexpr() {
			return getRuleContext(CondexprContext.class,0);
		}
		public TerminalNode OP() { return getToken(ExprParser.OP, 0); }
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public CondexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterCondexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitCondexpr(this);
		}
	}

	public final CondexprContext condexpr() throws RecognitionException {
		CondexprContext _localctx = new CondexprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_condexpr);
		try {
			setState(25);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(16); match(4);
				setState(17); condexpr();
				setState(18); match(2);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(20); cond();
				setState(21); match(OP);
				setState(22); condexpr();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(24); cond();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionexprContext extends ParserRuleContext {
		public ActionexprContext actionexpr() {
			return getRuleContext(ActionexprContext.class,0);
		}
		public TerminalNode ACTION() { return getToken(ExprParser.ACTION, 0); }
		public ActionexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterActionexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitActionexpr(this);
		}
	}

	public final ActionexprContext actionexpr() throws RecognitionException {
		ActionexprContext _localctx = new ActionexprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_actionexpr);
		try {
			setState(30);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(27); match(ACTION);
				setState(28); actionexpr();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(29); match(ACTION);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ActionexprContext actionexpr() {
			return getRuleContext(ActionexprContext.class,0);
		}
		public CondexprContext condexpr() {
			return getRuleContext(CondexprContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32); match(1);
			setState(33); condexpr();
			setState(34); match(3);
			setState(35); actionexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\f(\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\34\n\4\3\5\3\5\3\5\5\5!\n\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\2\7\2\4\6\b\n\2\3\4\n\n\f\f%\2\f\3\2\2\2\4\16"+
		"\3\2\2\2\6\33\3\2\2\2\b \3\2\2\2\n\"\3\2\2\2\f\r\t\2\2\2\r\3\3\2\2\2\16"+
		"\17\5\2\2\2\17\20\7\t\2\2\20\21\5\2\2\2\21\5\3\2\2\2\22\23\7\6\2\2\23"+
		"\24\5\6\4\2\24\25\7\4\2\2\25\34\3\2\2\2\26\27\5\4\3\2\27\30\7\b\2\2\30"+
		"\31\5\6\4\2\31\34\3\2\2\2\32\34\5\4\3\2\33\22\3\2\2\2\33\26\3\2\2\2\33"+
		"\32\3\2\2\2\34\7\3\2\2\2\35\36\7\13\2\2\36!\5\b\5\2\37!\7\13\2\2 \35\3"+
		"\2\2\2 \37\3\2\2\2!\t\3\2\2\2\"#\7\3\2\2#$\5\6\4\2$%\7\5\2\2%&\5\b\5\2"+
		"&\13\3\2\2\2\4\33 ";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}