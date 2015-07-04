UTEID: ttn2365; vtn288;
FIRSTNAME: Thanh; Vinh;
LASTNAME: Nguyen; Nguyen;
CSACCOUNT: thanhcs; vinhcs;
EMAIL: thanhnguyencs@utexas.edu; vinhnnguyentx@gmail.com;

[Program 4]
[Description]
TIt is the same as the assignment. Besides,

Parser: I use common CLI to parse the command line input
http://commons.apache.org/proper/commons-cli/index.html

We used mixColumns code from professor.

[Finish]
We finished extra credit 1
Still working on extra credit 2

[Test Cases]
[Input of test 1]
[command line]
java AES e -length 128 keyFile testcase1
java AES e -length 128 keyFile testcase1.enc

keyFile
testcase1

[Output of test 1]
testcase1.enc
testcase1.enc.dec

[Input of test 2]
[command line]
java AES e -length 192 keyFile testcase2
java AES e -length 192 keyFile testcase2.enc

keyFile
testcase2

[Output of test 2]

testcase2.enc
testcase2.enc.dec

[Input of test 3]
[command line]
java AES e -length 256 keyFile testcase3
java AES e -length 256 keyFile testcase3.enc

keyFile
testcase3

[Output of test 3]
testcase3.enc
testcase3.enc.dec

[Input of test 4]
[command line]
java AES e -length 128 keyFile testcase4
java AES e -length 128 keyFile testcase4

keyFile
testcase4

[Output of test 4]
testcase4.enc
testcase4.enc.dec

