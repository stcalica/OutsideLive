# OutsideLive

Start a silent disco or party broadcast and stream directly to user's phones and/or sound equipment. 

A musician will create an event which will provide him with an IP address to connect to his musical equipment 
and be able to broadcast to users who sign on into the event. 

Inspired by trying to create an easier way to create a Silent Disco for free. 



How it works: 

1. "A DJ will create an event in the application."   
  The application will call our API which will then create a spot instance (a short lived server) on Outsidelive's servers which will be spun up to have icecast and then it will return with the IP Address 
    
2. "The DJ will wait for the response which will have an IP Address and will use that to put into his DAW."


3. "The event gets shared to all users on the app (or select individuals)" 


4. "Listeners will click on the event and will already have the broadcast to their phone" 
    In the background each event is associated with the icecast spot ip address and so they are connecting to that spot instance 
