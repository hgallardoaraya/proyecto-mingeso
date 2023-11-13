import Razon from "@/types/Razon";
import { stringify } from "querystring";

export default function PagarRazonesForm({razones}:{razones:Array<Razon>}){
  return(
    JSON.stringify(razones)
  )
}