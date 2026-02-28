const express = require('express'); // Import the express module
const path = require('path');
const fs = require('fs')
const app = express(); // Create an Express application instance
const PORT = 3000; // Define the port number


// Define a route for GET requests to the root URL (/)
//app.get('/', (req, res) => {
  //res.send('index.html'); // Send a response to the client
//});
app.use(express.urlencoded({ extended: true }))
app.get("/login", (req, res) => {
  res.sendFile(path.join(__dirname, "public", "login.html"));
});
app.get("/signup", (req, res) => {
  res.sendFile(path.join(__dirname, "public", "signup.html"));
});

app.post("/signup1", (req, res) => {
  const { username, password } = req.body;

  fs.readFile("public/users.txt", "utf8", (err, data) => {
    let users = {};
    console.log(err)

    if (data) {
      data.split("\n").forEach(line => {
        const [u, p] = line.split(":");
        if (u) users[u] = p;
      });
    }
    console.log(data)
    console.log(users)
    if (users[username]) {
      res.send("User already exists");
    } else {
      fs.appendFile("public/users.txt", `${username}:${password}\n`, () => {
        res.send("Signup successful! <a href='/login.html'>Login</a>");
      });
    }
  });
});


app.post("/login", (req, res) => {
  const { username, password } = req.body;
  
  fs.readFile("public/users.txt", "utf8", (err, data) => {
    let users = {};
    //console.log(err)

    if (data) {
      data.split("\n").forEach(line => {
        const [u, p] = line.split(":");
        if (u) users[u] = p;
      });
    }
    console.log(data)
    console.log(users)
    if (users[username] === password) {
      //res.send("Welcome, " + username);
      //document.cookie
      res.redirect(`index.html?username=${encodeURIComponent(username)}`)
    } else {
      res.send("Invalid username or password");
    }
  });
});

app.use(express.static(path.join(__dirname, 'public')));

// Start the server and listen on the specified port
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Server is running on http://localhost:${PORT}`); // Log a message when the server starts
});
