# Time Blade Rider

Test bidder which responds to OpenRTB.

## Getting Started

1. Start the application: `lein run`
2. Go to [localhost:5150](http://localhost:5150/) to see: `Time Blade Rider`
3. Read your app's source code at src/time_blade_rider/service.clj. Explore the docs of functions
   that define routes and responses.
4. Run your app's tests with `lein test`. Read the tests at test/time_blade_rider/service_test.clj.
5. Learn more! See the [Links section below](#links).


## Configuration

To configure logging see config/logback.xml. By default, the app logs to stdout and logs/.
To learn more about configuring Logback, read its [documentation](http://logback.qos.ch/documentation.html).


## Developing your service

1. Start a new REPL: `lein repl`
2. Start your service in dev-mode: `(def dev-serv (run-dev))`
3. Connect your editor to the running REPL session.
   Re-evaluated code will be seen immediately in the service.

### [Docker](https://www.docker.com/) container support

1. Build an uberjar of your service: `lein uberjar`
2. Build a Docker image: `sudo docker build -t time-blade-rider .`
3. Run your Docker image: `docker run -p 5150:5150 time-blade-rider`

### [OSv](http://osv.io/) unikernel support with [Capstan](http://osv.io/capstan/)

1. Build and run your image: `capstan run -f "5150:5150"`

Once the image it built, it's cached.  To delete the image and build a new one:

1. `capstan rmi time-blade-rider; capstan build`


## Links
* [Other examples](https://github.com/pedestal/samples)
