package server.dsl;

public class ParserException extends Exception
{

    private static final long serialVersionUID = 2692366083820590594L;
    
    public ParserException(String r)
    {
        super("Bad expression: '" + r + "'");
    }

}
