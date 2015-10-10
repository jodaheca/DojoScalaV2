package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.Person;
import models.{Person,DB}
import play.api.libs.json.Json

/* Clase Controlador que contiene los metodos implementados para una persona*/
class Application extends Controller {

/* Función por defecto que abre el formulario para ingresar una nueva persona*/
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  /*Definición de un objeto de la clase Person, obtiene los datos del formulario y los mapea en el objeto de la Clase Person*/
  val formPerson: Form[Person] = Form{
      mapping(
          "name" -> text,
          "age" -> number
          )(Person.apply)(Person.unapply)
  }
  
  /* Funcion que es llamada cuando el ususario envia el formulario, usa el objeto para mapear los campos del formulario y los lleva a la DB*/
  def addPerson = Action{ implicit request =>
    val persons = formPerson.bindFromRequest.get
    DB.save(persons)
    Redirect(routes.Application.index)    
  }
  
  /*Funcion que retorna un objeto Json con las personas que estan en la DB*/
  def getPerson = Action{
        val person = DB.query[Person].fetch
        Ok(Json.toJson(person))
  }

}
