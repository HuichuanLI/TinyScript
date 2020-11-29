package TinyScript;

import Common.PeekIterator;
import lexer.Token;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String source = "abcdefg if true";
        PeekIterator<Character> it = new PeekIterator<Character>(source.chars().mapToObj(c -> (char) c));

//        while(it.hasNext()){
//            System.out.println("it:"+it.peek());
//            it.next();
//        }

        Token token1 = Token.makeVarOrKeyword(it);
        System.out.println(token1.toString());
        it.next();
        Token token2 = Token.makeVarOrKeyword(it);
        System.out.println(token2.toString());


    }
}
