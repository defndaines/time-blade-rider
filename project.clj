(defproject time-blade-rider "0.0.1-SNAPSHOT"
  :description "Test bidder which responds to OpenRTB."
  :url "https://github.com/defndaines/time-blade-rider"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"]
                 [org.clojure/data.json "0.2.6"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  ;; If you use HTTP/2 or ALPN, use the java-agent to pull in the correct alpn-boot dependency
  ;:java-agents [[org.mortbay.jetty.alpn/jetty-alpn-agent "2.0.5"]]
  :profiles {:dev {:aliases {"crepl" ["trampoline" "run" "-m" "clojure.main/main"]
                             "srepl" ["with-profile" "srepl" "trampoline" "run" "-m" "clojure.main/main"]
														 "run-dev" ["trampoline" "run" "-m" "time-blade-rider.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.2"]]}
						 :srepl {:jvm-opts ^:replace ["-d64" "-server"
																					"-XX:+UseG1GC"
																					"-D\"clojure.compiler.direct-linking=true\""
																					"-Dclojure.server.repl={:port 5150 :accept clojure.core.server/repl}"]}
						 :uberjar {:global-vars ^:replace {*warn-on-reflection* true
																							 *unchecked-math* :warn-on-boxed
																							 *assert* false}
											 :jvm-opts ["-D\"clojure.compiler.direct-linking=true\""
																	"-D\"io.pedestal.log.overrideLogger=nil\""
																	"-D\"io.pedestal.log.defaultMetricsRecorder=nil\""]
                       :aot [time-blade-rider.server]
                       :main time-blade-rider.server}}
	:jvm-opts ^:replace ["-D\"clojure.compiler.direct-linking=true\""
											 ;; Turn off Pedestal's Metrics
											 "-D\"io.pedestal.defaultMetricsRecorder=nil\""
											 "-Xms1g"
											 "-XX:+UseG1GC"
											 "-XX:+AggressiveOpts"
											 "-XX:+UseCompressedOops"]
  :global-vars {*warn-on-reflection* true
                *unchecked-math* :warn-on-boxed
                *assert* true}
  :main ^{:skip-aot true} time-blade-rider.server
  :uberjar-name "time-blade-rider-standalone.jar")
