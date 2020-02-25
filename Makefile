server: server.cpp
	g++ server.cpp -pthread -o server

clean:
	rm -f server
