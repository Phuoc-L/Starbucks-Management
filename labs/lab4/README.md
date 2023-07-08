# CMPE 172 - Lab #4 Notes

## Spring-Security

When accessing the app on port 8080, It started me on the home page.
![Spring-Security_Home](./Images/Spring-Security_Home.png)

When I tried going to /hello, the app automatically redirected me to /login.
![Spring-Security_Login](./Images/Spring-Security_Login.png)

After logging in, I was able to access the /hello page.
![Spring-Security_Hello](./Images/Spring-Security_Hello.png)

---

## Spring-gumball-v2

![Spring-Docker](./Images/Spring-Docker.png)

![Spring-Docker-ps](./Images/Spring-Docker-ps.png)

![Spring-Docker-App](./Images/Spring-Docker-app.png)

### Do you see any errors that were observed in Spring Gumball (Version 1)?

    -Yes, when using the app, whenever you click any button and not only when you reload tge page, you will be cycle between the two servers, gumball 1 and gumball 2.

### Why or Why Not? Explain the technical details to support your observation

    -I think this is because the app is not reliant on session anymore, and it is not sticky, so you will be cycle between the two gumball containers. 

---

## Spring Gumball Replay Attack

Using the initial version of Spring Gumball v2.0, doing a replay attack using tamper dev, worked. Here you can see that a gumball rolled out even though I did not insert a coin.

![Replay-Work](./Images/Replay-Work.png)

Now, with a modified version of gumball v2.0, doing a replay attack with tamper dev did not work. The replayed hash does not match with the correct hash, so the attack is thwarted.

![Replay-Not-Work](./Images/Replay-Not-Work.png)
