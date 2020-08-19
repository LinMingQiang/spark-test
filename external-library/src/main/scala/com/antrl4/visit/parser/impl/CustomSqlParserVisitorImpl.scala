package com.antrl4.visit.parser.impl

import com.antlr4.parser.{CustomSqlParserBaseVisitor, CustomSqlParserParser}
import com.antrl4.visit.operation.impl.{
  AbstractVisitOperation,
  CheckpointVisitOperation,
  HelloWordVisitOperation
}

class CustomSqlParserVisitorImpl
    extends CustomSqlParserBaseVisitor[AbstractVisitOperation] {

  override def visitHelloWordStatement(
      ctx: CustomSqlParserParser.HelloWordStatementContext)
    : AbstractVisitOperation = {
    HelloWordVisitOperation(ctx.word.getText)
  }

  override def visitCheckpointStatement(
      ctx: CustomSqlParserParser.CheckpointStatementContext)
    : AbstractVisitOperation = {
    CheckpointVisitOperation(ctx.table.getText, ctx.location.getText)
  }

  /**
    * 先进的这个，再进 @link  visitCheckpointStatement
    *
    * @param ctx the parse tree
   **/
  override def visitCheckpoint(
      ctx: CustomSqlParserParser.CheckpointContext): AbstractVisitOperation = {
    visitCheckpointStatement(ctx.checkpointStatement())
  }

  override def visitHelloWord(
      ctx: CustomSqlParserParser.HelloWordContext): AbstractVisitOperation = {
    visitHelloWordStatement(ctx.helloWordStatement())
  }

  /**
    *
    */
  override def visitSelectHbase(
      ctx: CustomSqlParserParser.SelectHbaseContext): AbstractVisitOperation = {
    val hbaseInfo = ctx.hBaseSearchState()
    import scala.collection.JavaConverters._
    hbaseInfo.familyColumns.asScala.foreach(family => {
      val columns = family.columnDefineState()
      columns.asScala.foreach(colms => {
        println(
          hbaseInfo.tableName.getText,
          family.familyName.getText,
          colms.colName.getText,
          colms.colType.getText
        )
      })
    })
    null
  }

}
