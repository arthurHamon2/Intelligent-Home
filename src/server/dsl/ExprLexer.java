package server.dsl;
// Generated from Expr.g by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, WS=5, OP=6, COMP=7, REAL=8, ACTION=9, 
		MEASURE=10;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'ON'", "')'", "'DO'", "'('", "WS", "OP", "COMP", "REAL", "ACTION", "MEASURE"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "WS", "OP", "COMP", "INT", "REAL", "ACTION", 
		"MEASURE"
	};


	public ExprLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expr.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 4: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\fO\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3"+
		"\5\3\6\6\6%\n\6\r\6\16\6&\3\6\3\6\3\7\3\7\3\7\3\7\5\7/\n\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\5\b:\n\b\3\t\6\t=\n\t\r\t\16\t>\3\n\3\n\3\n\3"+
		"\n\3\n\5\nF\n\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\2\r\3\3\1\5\4\1\7"+
		"\5\1\t\6\1\13\7\2\r\b\1\17\t\1\21\2\1\23\n\1\25\13\1\27\f\1\3\2\5\5\13"+
		"\f\17\17\"\"\4>>@@\3\62;U\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\3\31\3\2\2\2\5\34\3\2\2\2\7\36\3\2\2\2\t!\3\2\2\2\13$\3\2"+
		"\2\2\r.\3\2\2\2\179\3\2\2\2\21<\3\2\2\2\23E\3\2\2\2\25G\3\2\2\2\27L\3"+
		"\2\2\2\31\32\7Q\2\2\32\33\7P\2\2\33\4\3\2\2\2\34\35\7+\2\2\35\6\3\2\2"+
		"\2\36\37\7F\2\2\37 \7Q\2\2 \b\3\2\2\2!\"\7*\2\2\"\n\3\2\2\2#%\t\2\2\2"+
		"$#\3\2\2\2%&\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'(\3\2\2\2()\b\6\2\2)\f\3\2"+
		"\2\2*+\7(\2\2+/\7(\2\2,-\7~\2\2-/\7~\2\2.*\3\2\2\2.,\3\2\2\2/\16\3\2\2"+
		"\2\60\61\7?\2\2\61:\7?\2\2\62:\t\3\2\2\63\64\7>\2\2\64:\7?\2\2\65\66\7"+
		"@\2\2\66:\7?\2\2\678\7#\2\28:\7?\2\29\60\3\2\2\29\62\3\2\2\29\63\3\2\2"+
		"\29\65\3\2\2\29\67\3\2\2\2:\20\3\2\2\2;=\t\4\2\2<;\3\2\2\2=>\3\2\2\2>"+
		"<\3\2\2\2>?\3\2\2\2?\22\3\2\2\2@A\5\21\t\2AB\7\60\2\2BC\5\21\t\2CF\3\2"+
		"\2\2DF\5\21\t\2E@\3\2\2\2ED\3\2\2\2F\24\3\2\2\2GH\7R\2\2HI\5\21\t\2IJ"+
		"\7<\2\2JK\5\21\t\2K\26\3\2\2\2LM\7O\2\2MN\5\21\t\2N\30\3\2\2\2\b\2&.9"+
		">E";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}