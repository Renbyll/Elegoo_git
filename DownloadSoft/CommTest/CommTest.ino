int LED = 13;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(LED,OUTPUT);
  Serial.println("OK");
  digitalWrite(LED, HIGH);
}

void loop() {
  // put your main code here, to run repeatedly:
}
