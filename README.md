<h1 align="center">Java Socket Chat Application</h1>.

<h3>A simple terminal-based client-server chat application built using Java Sockets. This project demonstrates the fundamentals of TCP/IP networking in Java, multi-threaded server design, and basic real-time message exchange between multiple clients and a server</h3>

## 🛠 Features

- ✅ Text-based chat communication
- ✅ Multi-client support via threading
- ✅ Custom client display names
- ✅ Server and client disconnection handling
- ✅ Real-time bidirectional message flow
- ✅ Portable and lightweight (no external libraries)

---
## ✅ How It Works

1. The **server** listens on port `2020` and spawns a new thread for each client connection.
2. Each **client** connects to the server using a TCP socket.
3. Clients and server exchange messages in real time.
4. If any participant types `bye`, their connection is gracefully closed.

### When prompted:

- **Enter Server IP Address**: Enter `127.0.0.1` if running on the same machine as the server, or provide the actual IP of the server.
- **Enter your display name**: This name will be prefixed to your messages in the chat.
