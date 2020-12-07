package parser.util;

import org.apache.commons.lang.StringUtils;
import parser.ast.ASTNode;
import parser.ast.Factor;

import java.util.ArrayList;

/**
 * Author : lihuichuan
 * Time   : 2020/12/7
 */
public class ParserUtils {

    public static String toPostfixExpression(ASTNode node) {

        if(node instanceof Factor) {
            return node.getLexeme().getValue();
        }

        ArrayList prts = new ArrayList<String>();
        for(ASTNode child : node.getChildren()) {
            prts.add(toPostfixExpression(child));
        }
        String lexemeStr = node.getLexeme() != null ? node.getLexeme().getValue() : "";
        if(lexemeStr.length() > 0) {
            return StringUtils.join(prts, " ") + " " + lexemeStr;
        } else {
            return StringUtils.join(prts, " ");
        }

    }
}