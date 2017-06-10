(ns time-blade-rider.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [clojure.data.json :as json]
            [ring.util.response :as ring-resp]))

(defn home-page
  [request]
  (ring-resp/response "Time Blade Rider"))

(defn nurl
  "Win notice URL called by the exchange if the bid wins (not necessarily
   indicative of a delivered, viewed, or billable ad); optional means of
   serving ad markup. Substitution macros (Section 4.4) may be included in
   both the URL and optionally returned markup."
  [request]
  (ring-resp/response "Thanks!"))

(defn burl
  "Billing notice URL called by the exchange when a winning bid becomes
   billable based on exchange-specific business policy (e.g., typically
   delivered, viewed, etc.). Substitution macros (Section 4.4) may be
   included."
  [request]
  (ring-resp/response "Cha-ching!"))

(defn lurl
  "Loss notice URL called by the exchange when a bid is known to have been
   lost. Substitution macros (Section 4.4) may be included.
   Exchange-specific policy may preclude support for loss notices or the
   disclosure of winning clearing prices resulting in ${AUCTION_PRICE}
   macros being removed (i.e., replaced with a zero-length String)."
  [request]
  (ring-resp/response "¯\\_(ツ)_/¯"))

(defn gen-response
  [req]
  {:id (get req :id (str (java.util.UUID/randomUUID)))
   :bidid (str (java.util.UUID/randomUUID))
   :seatbid [{:bid
              [{:id (str (java.util.UUID/randomUUID))
                ;; Only grab the first impression for now.
                :impid (get-in req [:imp 0 :id] (str (java.util.UUID/randomUUID)))
                :price (rand 2.5)
                ; :nurl (route/url-for "/nurl")
                ; :burl (route/url-for "/burl")
                ; :lurl (route/url-for "/lurl")
                }]}]})

(defn handle-bid
  [request]
  ;; To simulate typical delays, wait before responding.
  (let [wait (+ 40 (rand-int 50) (rand-int 50))]
    (Thread/sleep wait)
    (-> (:json-params request)
        gen-response
        (http/json-response))))

;; Defines "/" and "/about" routes with their associated :get handlers.
;; The interceptors defined after the verb map (e.g., {:get home-page}
;; apply to / and its children (/about).
(def common-interceptors [(body-params/body-params) http/html-body])

;; Tabular routes
(def routes #{["/" :get (conj common-interceptors `home-page)]
              ["/bid" :post (conj common-interceptors `handle-bid)]
              ["/nurl" :post (conj common-interceptors `nurl)]
              ["/burl" :post (conj common-interceptors `burl)]
              ["/lurl" :post (conj common-interceptors `lurl)]})


;; Consumed by time-blade-rider.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ;; You can bring your own non-default interceptors. Make
              ;; sure you include routing and set it up right for
              ;; dev-mode. If you do, many other keys for configuring
              ;; default interceptors will be ignored.
              ;; ::http/interceptors []
              ::http/routes routes

              ;; Uncomment next line to enable CORS support, add
              ;; string(s) specifying scheme, host and port for
              ;; allowed source(s):
              ;;
              ;; "http://localhost:8080"
              ;;
              ;;::http/allowed-origins ["scheme://host:port"]

              ;; Root for resource interceptor that is available by default.
              ::http/resource-path "/public"

              ;; Either :jetty, :immutant or :tomcat (see comments in project.clj)
              ::http/type :jetty
              ;;::http/host "localhost"
              ::http/port 5150
              ;; Options to pass to the container (Jetty)
              ::http/container-options {:h2c? true
                                        :h2? false
                                        ;:keystore "test/hp/keystore.jks"
                                        ;:key-password "password"
                                        ;:ssl-port 8443
                                        :ssl? false}})
