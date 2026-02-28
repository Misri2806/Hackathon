const express = require('express'); // Import the express module
const path = require('path');
const app = express(); // Create an Express application instance
const PORT = 3000; // Define the port number

// Define a route for GET requests to the root URL (/)
//app.get('/', (req, res) => {
  //res.send('index.html'); // Send a response to the client
//});
app.use(express.static(path.join(__dirname, 'public')));
// Start the server and listen on the specified port
app.listen(PORT, '0.0.0.0', () => {
  console.log(`Server is running on http://localhost:${PORT}`); // Log a message when the server starts
});
