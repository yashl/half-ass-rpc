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

#define SA struct sockaddr 

typedef struct
{
	char OS[16];
	char valid;
} GET_LOCAL_OS;

typedef struct
{
	unsigned int time;
	char valid;
} GET_LOCAL_TIME;

void TCPServer(int port);

int main() 
{
	/**
	 * 
	 * SET PORT NUMBER HERE
	 * 
	 */
	TCPServer(8080); //testing for localos
	TCPServer(9000); //testing for localtime
	return 0;
} 

char* GetLocalOS(unsigned char* param)
{
	char* buffer = "macOS"; //OS[16] = macOS and valid = 1
	return buffer;
}

int getCurTime()
{
  time_t t = time(NULL);
  struct tm timeinfo = *localtime(&t);

  int cur_time = (timeinfo.tm_min*100) + (timeinfo.tm_sec);
  return cur_time;
}

char* GetLocalTime(GET_LOCAL_TIME *ds)
{
	char* buffer = new char[5];
	int time = getCurTime();
	printf("Current Time: %d\n", time);
	snprintf(buffer, 5, "%d", time);
	return buffer;
}

void CommandListener(int sockfd) 
{
	unsigned char cmd[104]; 			bzero(cmd, 104);
	unsigned char params_length[4];		bzero(params_length, 4);
	char* send, buffer;

	read(sockfd, &cmd, sizeof(cmd));

	int x = 0;
	for(int i = 100; i < 104; i++)
	{
		params_length[x] = cmd[i];
		x++;
	}
	int length = ntohl(*((int*) &params_length[0]));
	printf("Length is %d\n", length);

	unsigned char bytes[length];	bzero(bytes, length);
	read(sockfd, &bytes, sizeof(bytes));

	unsigned char* temp = &bytes[0];

	if(strstr((const char *) cmd, "GetLocalTime") != NULL)
	{
		printf("GetLocalTime requested!\n");
		send = GetLocalTime(NULL);
		write(sockfd, send, 4);
	}
	else if(strstr((const char *) cmd, "GetLocalOS") != NULL)
	{
		printf("GetLocalOS requested!\n");
		send = GetLocalOS(temp);
		write(sockfd, send, 6);
	}
	else
	{
		printf("Unknown Request\n");
	}
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
	CommandListener(connfd); 

	// After chatting close the socket 
	close(sockfd); 
}

