# OpenSDS multicloud API test automation framework

This is a framework build in JAVA. It leverages JUNIT for running and reporting.
For multi-cloud (mc), our test sequence is fairly similar for all tests, like below, with an example of create bucket and ## upload object

> Create bucket

Call add backend API, it should return success
Call the create bucket API on OSDS
Check for success
Now, call the list buckets API on OSDS
Response should include the newly created bucket
Declare success/failure

> Upload object

Call add backend API, it should return success
Call the create bucket API on OSDS
Check for success
Now, call Upload Object API on OSDS
Check for success
If success, check for the object in the cloud backend, using cloud backend API
It should exist on the backend
If 5 is success and 7 is success, declare success
else declare failure


## Now, this framework will let us

- Use our mc AP as is to create test cases
- Use JSON/XML inputs and responses, convert them to POJO so results can be checked
- Read test inputs from .json files, run the same test case many times with different data
- Generate basic HTML reports and Google sheets reports to persist
- Can run on any build as long as <IP>:8089 is known
- Once we have enough test cases, we can also get code coverage
- Can be integrated with CI at a later date

