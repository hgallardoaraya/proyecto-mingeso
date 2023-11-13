"use client";

import { FormEvent, useState } from "react";
import importarExamenes from "./actions";
import { Response } from "@/types/Responses";

export default function ImportarExamenesForm(){
  const [archivo, setArchivo]:any = useState(null);

  const handleSubmit = async (e:FormEvent) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("archivo", archivo);
    const response:Response = await importarExamenes(formData);
    alert(response.message);
  }

  return(
    <form>
      <input type="file" onChange={(e:any) => {setArchivo(e.target.files[0])}} />
      <button onClick={handleSubmit} className="px-3 py-2 text-white bg-blue-600 rounded-md">IMPORTAR EXAMEN</button>
    </form>
  )
}