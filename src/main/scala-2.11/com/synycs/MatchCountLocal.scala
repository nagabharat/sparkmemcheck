package com.synycs

/**
  * Created by synycs on 27/12/16.
  */
case class MatchCountLocal(val count:Int) {

  def + (matchCount: MatchCountLocal):MatchCountLocal={

    return MatchCountLocal(1)
  }

}
