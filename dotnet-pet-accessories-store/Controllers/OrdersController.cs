// Controllers/OrdersController.cs (API Controller)
using Microsoft.AspNetCore.Mvc;
using PetAccessoriesStore.Models;
using PetAccessoriesStore.Models.DTOs;
using PetAccessoriesStore.Services;

namespace PetAccessoriesStore.Controllers
{
    [ApiController]
    [Route("api/orders")]
    public class OrdersController : ControllerBase
    {
        private readonly IOrderService _orderService;

        public OrdersController(IOrderService orderService)
        {
            _orderService = orderService;
        }

        [HttpPost("draft")]
        public async Task<IActionResult> CreateDraftOrder([FromBody] CreateOrderRequest request)
        {
            try
            {
                var order = await _orderService.CreateDraftOrderAsync(request);
                return Ok(order);
            }
            catch (Exception ex)
            {
                return BadRequest($"Error creating order: {ex.Message}");
            }
        }

        [HttpPost("{id}/confirm")]
        public async Task<IActionResult> ConfirmOrder(int id)
        {
            var order = await _orderService.ConfirmOrderAsync(id);
            if (order == null)
            {
                return NotFound();
            }
            return Ok(order);
        }

        [HttpGet]
        public async Task<IActionResult> GetOrders()
        {
            var orders = await _orderService.GetAllOrdersAsync();
            return Ok(orders);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetOrder(int id)
        {
            var order = await _orderService.GetOrderByIdAsync(id);
            if (order == null)
            {
                return NotFound();
            }
            return Ok(order);
        }
    }
}