package analytics
import static org.edx.jenkins.dsl.AnalyticsConstants.common_multiscm
import static org.edx.jenkins.dsl.AnalyticsConstants.common_parameters
import static org.edx.jenkins.dsl.AnalyticsConstants.from_date_interval_parameter
import static org.edx.jenkins.dsl.AnalyticsConstants.to_date_interval_parameter
import static org.edx.jenkins.dsl.AnalyticsConstants.common_log_rotator
import static org.edx.jenkins.dsl.AnalyticsConstants.common_wrappers
import static org.edx.jenkins.dsl.AnalyticsConstants.common_publishers
import static org.edx.jenkins.dsl.AnalyticsConstants.common_triggers

class LoadJsonEventsToBigquery {
    public static def job = { dslFactory, allVars ->
        dslFactory.job("load-json-events-to-bigquery") {
            logRotator common_log_rotator(allVars)
            parameters common_parameters(allVars)
            parameters from_date_interval_parameter(allVars)
            parameters to_date_interval_parameter(allVars)
            parameters {
                stringParam('OUTPUT_URL', allVars.get('OUTPUT_URL', ''))
                stringParam('CREDENTIALS', allVars.get('CREDENTIALS', ''))
                stringParam('EXTRA_ARGS', allVars.get('EXTRA_ARGS', ''),
                '' + $/
  Examples:

  * --event-record-type JsonEventRecord
  * --max-bad-records=1
  * --overwrite
  /$)
                stringParam('DATASET', allVars.get('DATASET', ''))
            }
            multiscm common_multiscm(allVars)
            triggers common_triggers(allVars)
            wrappers common_wrappers(allVars)
            publishers common_publishers(allVars)
            steps {
                shell(dslFactory.readFileFromWorkspace('dataeng/resources/load-json-events-to-bigquery.sh'))
            }
        }
    }
}
