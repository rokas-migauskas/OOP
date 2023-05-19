// Generated from Pseudocode.g4 by ANTLR 4.12.0
package com.game;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PseudocodeParser}.
 */
public interface PseudocodeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PseudocodeParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PseudocodeParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(PseudocodeParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(PseudocodeParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#move}.
	 * @param ctx the parse tree
	 */
	void enterMove(PseudocodeParser.MoveContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#move}.
	 * @param ctx the parse tree
	 */
	void exitMove(PseudocodeParser.MoveContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#checkObstacle}.
	 * @param ctx the parse tree
	 */
	void enterCheckObstacle(PseudocodeParser.CheckObstacleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#checkObstacle}.
	 * @param ctx the parse tree
	 */
	void exitCheckObstacle(PseudocodeParser.CheckObstacleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(PseudocodeParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(PseudocodeParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(PseudocodeParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(PseudocodeParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(PseudocodeParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(PseudocodeParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(PseudocodeParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(PseudocodeParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#setVariable}.
	 * @param ctx the parse tree
	 */
	void enterSetVariable(PseudocodeParser.SetVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#setVariable}.
	 * @param ctx the parse tree
	 */
	void exitSetVariable(PseudocodeParser.SetVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#delay}.
	 * @param ctx the parse tree
	 */
	void enterDelay(PseudocodeParser.DelayContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#delay}.
	 * @param ctx the parse tree
	 */
	void exitDelay(PseudocodeParser.DelayContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(PseudocodeParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(PseudocodeParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PseudocodeParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PseudocodeParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PseudocodeParser#comparison_operator}.
	 * @param ctx the parse tree
	 */
	void enterComparison_operator(PseudocodeParser.Comparison_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PseudocodeParser#comparison_operator}.
	 * @param ctx the parse tree
	 */
	void exitComparison_operator(PseudocodeParser.Comparison_operatorContext ctx);
}