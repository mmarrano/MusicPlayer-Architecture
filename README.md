# SmartSound

The project consists of two modules (server side and front-end).

Note: In order to run the server side one needs to have MySql properly installed on the same machine (MySql user: root, password: root). Install a database called "smartsync" <br />
In order to run the client side one needs to have node v4.x.x or higher and npm 2.14.7 installed properly. <br />

Server side:
1. Follow the very firts 3 instructions in the ReadMe file in the smartsync-master project.
2. run the following command in the main folder of the server project for the deployment: `./ss_serve.bash --build` <br />
    Note: ignore the errors in the deployment process, wait until it starts all the services.

Client side:
1. Follow the instuctions until `npm start -- --app baz` (the last command) <br />
    Note: ignore the errors in the deployment process, wait until it opens up a login page on a browser. <br />
    If you are able to login into the system, you should be good to move on.
    
    
## Authors

* **Gabe Klein**
* **Dohyun Kim**
* **Mark Marrano**
* **Jose Lopez**
