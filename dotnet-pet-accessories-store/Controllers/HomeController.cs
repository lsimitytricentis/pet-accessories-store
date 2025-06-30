using Microsoft.AspNetCore.Mvc;

namespace PetAccessoriesStore.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}