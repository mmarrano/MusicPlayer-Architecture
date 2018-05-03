#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{
  printf("Server Start\n");
  int listenfd = 0, connfd = 0;
    struct sockaddr_in serv_addr; 

    char sendBuff[1025];
    char testBuff[2000];
    time_t ticks; 

    listenfd = socket(AF_INET, SOCK_STREAM, 0);
    memset(&serv_addr, '0', sizeof(serv_addr));
    memset(sendBuff, '0', sizeof(sendBuff)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(5000); 

    bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr)); 

    listen(listenfd, 10); 
    int pid;
    while(1)
    {
      printf("LaLaLaLa\n");
      connfd = accept(listenfd, (struct sockaddr*)NULL, NULL); 
        ticks = time(NULL);
        snprintf(sendBuff, sizeof(sendBuff), "%.24s\r\n", ctime(&ticks));
	//system("killall omxplayer.bin");
	//execlp("/usr/bin/omxplayer", " ", "/home/pi/Music/first.mp3", NULL);
        //write(connfd, sendBuff, strlen(sendBuff));
	int n;
	while((n = read(connfd, testBuff, sizeof(testBuff)-1)) > 0) {
	  testBuff[n] = 0;
	  if(fputs(testBuff, stdout) == EOF) {
	    printf("\n Error : Fputs error\n");
	  }
	}

	if(n < 0) {
	  printf("\n Read error \n");
	}
	printf("\n");
	printf("%s\n", testBuff);

	//write(connfd, sendBuff, strlen(sendBuff));
	
        close(connfd);
	pid = fork();
	if(pid == 0) {
	  system("killall omxplayer.bin");
	  execlp("/usr/bin/omxplayer", " ", testBuff, NULL);
	  break;
	}
        sleep(1);
     }
}
