var dataUrl = "https://bot-json-27jjqnuqo-leolecrado.vercel.app/data.json";
fetch(dataUrl)
	.then((res) => res.json())
	.then((json) => console.log(json));