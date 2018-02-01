(defproject dispatch-n-bug-mwe "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0-alpha2"]
                 [org.clojure/clojurescript "1.9.946"]
                 [reagent "0.8.0-alpha2"]
                 [re-frame "0.10.4"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.9"]]

    :plugins      [[lein-figwheel "0.5.15-SNAPSHOT"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "dispatch-n-bug-mwe.core/mount-root"}
     :compiler     {:main                 dispatch-n-bug-mwe.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            dispatch-n-bug-mwe.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}


    ]}

  )
