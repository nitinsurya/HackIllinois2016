TCPClient client;

byte server[] = { 40, 114, 2, 108 };

void setup()

{

  Serial.begin(9600);

}




char* concat(char *s1, char *s2)

{

    int len1 = strlen(s1);

    int len2 = strlen(s2);

    char *result = (char*)malloc(len1+len2+1);//+1 for the zero-terminator

    //in real code you would check for errors in malloc here

    memcpy(result, s1, len1);

    memcpy(result+len1, s2, len2);//+1 to copy the null-terminator

    *(result+len1+len2)='\0';

    return result;

}







void loop()

{

    

    float p=0.5, q=-0.3, r=2.5,s=-1, t=1;

    

    float x[]={0.01,0.10, 0.12,0.20,0.32};

    char *send;

    

  delay(1000);

  Serial.println("connecting...");




  if (client.connect(server, 80)) 

  {

    Serial.println("connected");

    

    

    sprintf(send, "[[%f,%f]:[%f,%f]:[%f,%f]:[%f,%f]:[%f,%f]]", p, x[0], q, x[1], r, x[2], s, x[3], t, x[4]);

    

    

    //float p,q,r,s,t,i;

    

    char *tmp = "GET /send_update.php?ecg=";

  tmp = concat(tmp, send);




//     client.println("GET /send_update.php?");

// client.println("ecg =");

// client.println(send);

client.println(tmp);

  free(tmp);

    

    client.println();

  } 

  else 

  {

    Serial.println("connection failed");

  }

 

}