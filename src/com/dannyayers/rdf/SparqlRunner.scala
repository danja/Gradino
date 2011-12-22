package com.dannyayers.rdf

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.query._

object SparqlRunner {

  def runQuery(model: Model, queryString: String): String = {
    System.out.println(queryString)
    val query = QueryFactory.create(queryString)
    //	val result : String =
    if (query.isSelectType)
      ResultSetFormatter.asXMLString(QueryExecutionFactory.create(query, model).execSelect)
      else
    if (query.isAskType)
      if (QueryExecutionFactory.create(query, model).execAsk)
        "true"
      else
        "false"
    else if (query.isDescribeType)
      RdfUtils.modelToString(QueryExecutionFactory.create(query, model).execDescribe)
    else if (query.isConstructType)
      RdfUtils.modelToString(QueryExecutionFactory.create(query, model).execConstruct)
    else
      "aaaaargh!"
  }
}
