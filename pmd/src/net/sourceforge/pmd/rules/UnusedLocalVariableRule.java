/*
 * User: tom
 * Date: Jun 18, 2002
 * Time: 11:02:09 AM
 */
package net.sourceforge.pmd.rules;

import java.util.Iterator;
import java.text.MessageFormat;

import net.sourceforge.pmd.ast.*;
import net.sourceforge.pmd.*;
import net.sourceforge.pmd.symboltable.*;

public class UnusedLocalVariableRule extends AbstractRule {
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (node.jjtGetParent().jjtGetParent() instanceof ASTLocalVariableDeclaration) {
            RuleContext ctx = (RuleContext)data;
            for (Iterator i =  node.getScope().getUnusedDeclarations(); i.hasNext();) {
                NameDeclaration symbol = (NameDeclaration)i.next();
                ctx.getReport().addRuleViolation(createRuleViolation(ctx, symbol.getLine(), MessageFormat.format(getMessage(), new Object[] {symbol.getImage()})));
            }
        }
        return data;
    }
}
