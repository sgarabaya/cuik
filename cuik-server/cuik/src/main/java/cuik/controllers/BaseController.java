package cuik.controllers;

import cuik.server.HttpContext;

// El Controller se instancia cada vez que hay un request
// No comparte estado con otros controllers del mismo tipo
public abstract class BaseController {
    // Se inyecta al instanciarse; es especifico de cada request
    protected HttpContext context;
}
