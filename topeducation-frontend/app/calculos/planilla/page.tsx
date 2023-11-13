"use client";

import calcularPlanilla from "./actions";

export default function CalcularPlanillaPage(){

  const handleSubmit = async () => {
    const response = await calcularPlanilla();    
    alert(response.message);
  }

  return(
    <div>
      <h1>Calcular planilla</h1>    
      <button className="px-3 py-2 text-white bg-blue-600 rounded-md" onClick={handleSubmit}>CALCULAR</button>
    </div>
  )
}