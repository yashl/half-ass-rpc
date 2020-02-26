server: server.cpp
	g++ server.cpp -w -pthread -o server

clean:
	rm -f server
