document.addEventListener('keydown', function(e){
    if (e.keyCode === 9) {
		e.preventDefault();
    	
    	const cep = document.getElementById('cep').value;
    		
	    const xhr = new XMLHttpRequest
	    xhr.open('GET', `https://viacep.com.br/ws/${cep}/json/`)
	
	    xhr.addEventListener('load', function(){
	        const resposta = xhr.responseText
	        // console.log(resposta)
	
	        const cepAdd = JSON.parse(resposta)
	        // console.log(cepAdd, typeof cepAdd)
	        // console.log(cepAdd.cep)
	        montaCep(cepAdd)
			
	    })
	
	    xhr.send()
	}
    
})

function montaCep(cep){
	document.getElementById('endereco').value = cep.logradouro;
	document.getElementById('bairro').value = cep.bairro;
	document.getElementById('estado').value = cep.uf;
	document.getElementById('cidade').value = cep.localidade;
}