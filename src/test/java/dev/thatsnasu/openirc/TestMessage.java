package dev.thatsnasu.openirc;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dev.thatsnasu.openirc.exceptions.MessageLengthExceededException;
import dev.thatsnasu.openirc.exceptions.MessagePrefixException;

public class TestMessage {
	
	 @Test
	 @DisplayName("Message prefix checks")
	 public void prefixChecks() {
		 assertThrows(MessagePrefixException.class, () -> new Message("prefix", "command", new String[] {"param", "param", "param"}));
		 assertThrows(MessagePrefixException.class, () -> new Message(": prefix", "command", new String[] {"param", "param", "param"}));
		 
	 }
	 
	 @Test
	 @DisplayName("Message length checks")
	 public void legthChecks() {
		 assertThrows(MessageLengthExceededException.class, () -> new Message(":prefix", "command", new String[] {
				 "ZOrk2e0nDCmQmvmn5MYbqs9owSX8qKFchVYJYKGqWCynjjaw10SGuOszffmLMr43u0LmUOMm25lS83NGOvIOlYitM3SFbC5"
				 + "yBx7yHTH8qFfIFBKKbk1hFjm6e6SmPk2J7cNB0f3gVuiLzWancom26mJQ9vzZE7TyTDGv5hAWwkF9aa98EZO9AtA3fy0J"
				 + "4uXB26QuQCI2G2sR8LAvgNXcFUJ59bQ8fXheRnwoB6ZKiL0cwGLOkoOLrmM2oNuuw4zfY4XS3USB683Qbhwdky3y7jawm"
				 + "IqIsj5QB3Kmc917WIHESeiD2y4d4SUucTpCROVQawCuz31fxiTaX9zxMW1pzChPRxYvpsGxLr3iSEl4hjIDquZx84W2n5"
				 + "oWCYiqw3J27A1UvAdJ5dE7n315EcMybNX64XRCsPd0IlFPeYaWqp4ajnOnjTk8y4YfCqvN7Jq1Wy5oz7ca2p8n6mgdSVL"
				 + "cHIn9bgA29y4n8RnK2FuFcExrqpW"}).getMessageString());
		 assertThrows(MessageLengthExceededException.class, () -> new Message("", "command", new String[] {
		 		"Bj62IX83J6OVLA5mFd0hkZCtWUGyXt09pFHCtIQ61ksU06RsCakL4fNU9cmRuQIFdDYbgAZY9rrURQENEdMUpQq2IqOE0EBR"
		 		+ "L7ClHwcgaGiLdA9KuRYvqwoeXj59Mmpg2JOwepcLAmgyVtKT0TEysOg8zBAph9L6mzvYxveMEqjnqA0SgFx6h1XKeraH0f"
		 		+ "nxegESV6XlahhCHqwT0yhEpu8LEihwqaRuiN3INSkipbgBo1kae8KRMeZDY1zcz4oz7eypJ3uUULbw5ZT6xlS0K5dBOHGf"
		 		+ "eZ65KknFqvixqArWBKw2lUx5ZpEOAYKcQ0vYNm59sm86pNtifBHZTgy9pOJ8Q00vhVJsVbLKpR25JWmvfOWJdd6T97AwPq"
		 		+ "XVTFsP4qYKDeAuwCN9dGMFO5oSGjTDH2xDxEui0g5yqcdftu3jBhY4Bsq3zr4P37QC52WdCNHQvRMVgm3WmAifdFNMjax0"
		 		+ "bUHIBuSIOTFgHy4LknVpZyO1FtOf7sC"}).getMessageString());
		 assertThrows(MessageLengthExceededException.class, () -> new Message(":prefix", "123", new String[] {
		 		"vTpuHhFxH9iQ34RGe5f1cPM75PXpvBPYsNsDOLWBCGKB6k3IEYSTSi24gj55XhiFIuDPQ5YxRmvnQoFInZaAqM1C4vaLzg4G"
		 		+ "1QgXh0uK723Ww9QTNXVXWD8rZtcswb2MWi9LWlcX6Xd9NfRTudd3lghK2GdbWz37KOXJUgj77Ns8GOPrIM50EzchWtrUYd"
		 		+ "Ndy52KyBdKKyTJDQvtkf7lDDjTNLVIGzJhPPg0UOqKuf4d0lz9qJJw0KGJ04lufCWT74jFCFLlo1of7tACQY5v11wz0cZU"
		 		+ "11cqcfmYE3hM0aH7onVHKHPFsuIl7Dm4Wxmf19RY8GzqbmCYsEV0eZhl0kVfFN2ifQimKyMmVJrqf9ypHggPqSsLM0uRB7"
		 		+ "CwHw82g6s8Oz73pPHbXQ9Z2l77JO3oG3osDQEg7oaEFTNpPULj5IjYfhCtU6dsYvy7Dm1sHliMV28i8FvZ421d8LCklXVU"
		 		+ "9t94xvUT5Fg6IKVRDJiwElVxLKzy"}).getMessageString());
		 assertThrows(MessageLengthExceededException.class, () -> new Message("", "123", new String[] {
		 		"HAnwOr7m9r0RgkS5ywcbEBRBSGTctPAFBjMYOgtMGZKjC7crivn18wJxHO00pHjxZNcjKxKBzQ86lcgGx3LxuHKDhWQ3gXib"
		 		+ "qVI5rUPrdmzfLLaSv3YSha6SKYbpvoDIsF5vh1I0v4J9cxrvMF4P2F3BQYTV8DH0ttMWJosBvIdAsz5X64Aj1nkxAXppWI"
		 		+ "8qrMQhqFDTg4rGU9mhOsyo9Vehadlj7ss3dLrcVRsXLOq7lwFXkzW5GReyydTQWk1TiU0TPtpneLLNFgRYzAb1fcJBw7kc"
		 		+ "cQmelpfuzZuMiH6uHjdCclxpVGqHa2EuMcTI2LMfIo54fQvGcO4EslTPrywG7MYbx8SonXWF57RRbMNHTPpIAp6VaYFyiS"
		 		+ "hlsu0Ly9nNzPJbJTdzBa7JuFKkpDt6h9RrLzcemlRa7lPAho4xC8HTLexdoRE97iuuaHx4qi55yBsHCWnRffUr0vMOAxRN"
		 		+ "aar7TK2JSinRXqSgdHjagMtEEZHXUHav3O0b"}).getMessageString());
	 }
}
