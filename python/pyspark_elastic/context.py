# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


from functools import partial

import pyspark.context
from pyspark_elastic.rdd import EsRDD


def monkey_patch_sc(sc):
	sc.__class__ = EsSparkContext
	sc.__dict__["esRDD"] = partial(EsSparkContext.esRDD, sc)
	sc.__dict__["esRDD"].__doc__ = EsSparkContext.esRDD.__doc__


class EsSparkContext(pyspark.context.SparkContext):

	def esRDD(self, resource_read=None, query='', **kwargs):
		return EsRDD(self, resource_read, query, **kwargs)
