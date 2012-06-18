/*
 * Copyright 2012 Vasily Shiyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package see.parser

import org.parboiled.scala.parserunners.ReportingParseRunner
import org.parboiled.scala.rules.Rule1
import see.tree.nodes.Untyped.Node
import see.exceptions.ParseException

class ScalaParser(val rule: Rule1[Node]) extends Parser[AnyRef] {
  def parse(input: String) = {
    val result = ReportingParseRunner.apply(rule).run(input)

    result.result.getOrElse(throw new ParseException(result))
  }
}
