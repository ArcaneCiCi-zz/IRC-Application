# IRC-Application
A simple Java based IRC Application, with the idea of complete anonymity.

Use any alias you want, any time, any place.

# Updates

- v1.1: Added support for custom ports on the server.

- v1.0: Released to the public.

## Compiling
    Run "mvn clean install" on both modules.

## Usage
    Running this application is very easy.
    I've gone ahead and done their own sections below.

### Client
    Run compiled JAR with the arguments:
    --username <name> --asseturl <url> 
    
    The default asset url is "ws://0.0.0.0:9099/connect"

### Server
    Run compiled JAR with the argument:
    --port <port>
    
    If no port is specified on launch, the default port is 9099.