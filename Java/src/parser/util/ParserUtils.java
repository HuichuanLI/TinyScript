package parser.util;

import org.apache.commons.lang.StringUtils;
import parser.ast.ASTNode;
import parser.ast.Factor;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Author : lihuichuan
 * Time   : 2020/12/7
 */
public class ParserUtils {

    public static String toPostfixExpression(ASTNode node) {

        if (node instanceof Factor) {
            return node.getLexeme().getValue();
        }

        ArrayList prts = new ArrayList<String>();
        for (ASTNode child : node.getChildren()) {
            prts.add(toPostfixExpression(child));
        }
        String lexemeStr = node.getLexeme() != null ? node.getLexeme().getValue() : "";
        if (lexemeStr.length() > 0) {
            return StringUtils.join(prts, " ") + " " + lexemeStr;
        } else {
            return StringUtils.join(prts, " ");
        }

    }

    public static String toBFSString(ASTNode root, int max) {
        LinkedList<ASTNode> queue = new LinkedList<ASTNode>();
        ArrayList res = new ArrayList<String>();

        queue.add(root);
        int c = 0;
        while (queue.size() > 0 && c++ < max) {
            ASTNode node = queue.poll();
            res.add(node.getLabel());
            for (ASTNode child : node.getChildren()) {
                queue.add(child);
            }
        }
        return StringUtils.join(res, " ");
    }
}