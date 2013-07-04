/*
Usage 1: define the default prefix by using an object with the property prefix as a parameter which contains a string value; {prefix: 'id'}
Usage 2: call the function UuidGenerator.uuid() with a string parameter p to be used as a prefix to generate a random uuid;
Usage 3: call the function UuidGenerator.uuid() with no parameters to generate a uuid with the default prefix; defaul prefix: '' (empty string)
*/

/*
Generate fragment of random numbers
*/
UuidGenerator = {};
UuidGenerator._uuid_default_prefix = '';
UuidGenerator._uuidlet = function () {
	return(((1+Math.random())*0x10000)|0).toString(16).substring(1);
};
/*
Generates random uuid
*/
UuidGenerator.uuid = function (p) {
	if (typeof(p) == 'object' && typeof(p.prefix) == 'string') {
		UuidGenerator._uuid_default_prefix = p.prefix;
	} else {
		p = p || UuidGenerator._uuid_default_prefix || '';
		return(p+UuidGenerator._uuidlet()+UuidGenerator._uuidlet()+"-"+UuidGenerator._uuidlet()+"-"+UuidGenerator._uuidlet()+"-"+UuidGenerator._uuidlet()+"-"+UuidGenerator._uuidlet()+UuidGenerator._uuidlet()+UuidGenerator._uuidlet());
	};
};
