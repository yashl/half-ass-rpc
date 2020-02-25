#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <pthread.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <ifaddrs.h>
#include <netdb.h>

#define PORT 8080 
#define SA struct sockaddr 

void TCPServer(int port);

int main() 
{
	TCPServer(8080);

	return 0;
} 

void TCPListener(int sockfd) 
{ 
	unsigned char b[4]; 
	int n; 
  
	bzero(b, 4); 

	int total_read = read(sockfd, &b, sizeof(b));
	printf("Total Read: %d\n", total_read);
	
	n = ntohl(*((int*) &b[0]));


	printf("From client: %d\n", n);

	//write(sockfd, buff, sizeof(buff)); 
}

void TCPServer(int port)
{
  	int sockfd, connfd;
	unsigned int len; 
	struct sockaddr_in servaddr, cli; 

	// create socket
	sockfd = socket(AF_INET, SOCK_STREAM, 0); 
	if (sockfd == -1) { 
		printf("socket creation failed...\n"); 
		exit(0); 
	} 
	else
		printf("socket successfully created..\n"); 
	bzero(&servaddr, sizeof(servaddr)); 

	// assign IP, PORT 
	servaddr.sin_family = AF_INET; 
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY); 
	servaddr.sin_port = htons(port); 

	// Binding newly created socket to given IP and verification 
	if ((bind(sockfd, (SA*)&servaddr, sizeof(servaddr))) != 0) { 
		printf("socket bind failed...\n"); 
		exit(0); 
	} 
	else
		printf("socket successfully binded..\n"); 

	// Now server is ready to listen and verification 
	if ((listen(sockfd, 5)) != 0) { 
		printf("listen failed...\n"); 
		exit(0); 
	} 
	else
		printf("server listening..\n"); 
	len = sizeof(cli); 

	// Accept the data packet from client and verification 
	connfd = accept(sockfd, (SA*)&cli, &len); 
	if (connfd < 0) { 
		printf("server acccept failed...\n"); 
		exit(0); 
	} 
	else
		printf("server acccept the client...\n"); 

	// Function for chatting between client and server 
	TCPListener(connfd); 

	// After chatting close the socket 
	close(sockfd); 
}

