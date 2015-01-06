(defproject quile/component-cljs "0.2.4"
  :description "Managed lifecycle of stateful objects"
  :url "https://github.com/quile/component-cljs"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :min-lein-version "2.1.3"  ; added :global-vars
  :dependencies [[quile/dependency-cljs "0.1.4" :exclusions [org.clojure/clojure]]]
  :deploy-repositories [["clojars" {:sign-releases false}]]
  :global-vars {*warn-on-reflection* true}
  :aliases {"test-all"
            ["with-profile" "clj1.4:clj1.5:clj1.6:clj1.7" "test"]}

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :cljs}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :clj}

                  {:source-paths ["test/cljx"]
                   :output-path "target/test-classes"
                   :rules :cljs}]}

  :cljsbuild {:test-commands {"node"    ["node" "test-node/bin/runner-none.js" "target/test-node" "target/test-node.js"]}
              :builds [{:id "component"
                        :source-paths ["target/classes"]
                        :compiler {:output-to "target/component.js"
                                   :optimizations :simple
                                   :target :nodejs
                                   :pretty-print true}}
                       {:id "test-node"
                        :source-paths ["target/classes" "target/test-classes"]
                        :compiler {:output-to     "target/test-node.js"
                                   :target :nodejs ;;; this target required for node, plus a *main* defined in the tests.
                                   :output-dir    "target/test-node"
                                   :optimizations :none
                                   :pretty-print  true}}
                       ]}

  :profiles {:dev {:hooks        [cljx.hooks]
                   :test-paths   ["target/test-classes"]
                   :dependencies [[org.clojure/clojure "1.6.0"]
                                  [org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/clojurescript "0.0-2665"]]
                   :source-paths ["target/classes"]
                   :plugins      [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
                                  [com.cemerick/clojurescript.test "0.3.1"]
                                  [lein-cljsbuild "1.0.4-SNAPSHOT"]]}
             :clj1.7 {:dependencies [[org.clojure/clojure "1.7.0-master-SNAPSHOT"]]
                      :repositories {"sonatype-oss-public"
                                     "https://oss.sonatype.org/content/groups/public"}}
             :clj1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :clj1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :clj1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}})
