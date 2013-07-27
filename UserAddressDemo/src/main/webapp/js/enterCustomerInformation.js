function notEmpty(elem) {
	if (elem.value.length == 0) {
		return false;
	}
	return true;
}

function isNumeric(elem) {
	var numericExpression = /^[0-9]+$/;
	if (elem.value.match(numericExpression)) {
		return true;
	} else {
		return false;
	}
}

function isAlphabet(elem) {
	var alphaExp = /^[a-zA-Z0-9\u00A1-\uFFFF\_ .-@]+$/;
	if (elem.value.match(alphaExp)) {
		return true;
	} else {
		return false;
	}
}

function isAlphanumeric(elem) {
	var alphaExp = /^[a-zA-Z0-9\u00A1-\uFFFF\_ .-@]+$/;
	if (elem.value.match(alphaExp) && !isNumeric(elem)) {
		return true;
	} else {
		return false;
	}
}

function isFloat(elem) {
	if (elem.value.indexOf(".") < 0) {
		return false;
	} else {
		if (parseFloat(elem.value)) {
			return true;
		} else {
			return false;
		}
	}
}

function taskFormValidator() {
	var i = 0;
	var myInputs = new Array();

	myInputs[i] = document.getElementById("enteredAddressLine2");
	i++;

	myInputs[i] = document.getElementById("enteredAddressLine1");
	i++;

	myInputs[i] = document.getElementById("enteredCity");
	i++;

	myInputs[i] = document.getElementById("enteredFirstName");
	i++;

	myInputs[i] = document.getElementById("enteredState");
	i++;

	myInputs[i] = document.getElementById("enteredZipcode");
	i++;

	myInputs[i] = document.getElementById("enteredLastName");
	i++;

	myInputs[i] = document.getElementById("enteredNickName");
	i++;

	var j = 0;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredAddressLine2");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredAddressLine1");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredCity");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredFirstName");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredState");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredZipcode");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredLastName");
		myInputs[j].focus();
		return false;
	}
	j++;

	if (notEmpty(myInputs[j]) && !isAlphanumeric(myInputs[j])) {
		alert("Please enter valid enteredNickName");
		myInputs[j].focus();
		return false;
	}
	j++;

	return true;
}