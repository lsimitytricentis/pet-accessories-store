using Microsoft.EntityFrameworkCore;
using PetAccessoriesStore.Data;
using PetAccessoriesStore.Models;
using PetAccessoriesStore.Models.DTOs;

namespace PetAccessoriesStore.Services
{
    public class OrderService : IOrderService
    {
        private readonly ApplicationDbContext _context;

        public OrderService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<OrderResponse> CreateDraftOrderAsync(CreateOrderRequest request)
        {
            var order = new Order
            {
                OrderNumber = GenerateOrderNumber(),
                Email = request.Email,
                FirstName = request.FirstName,
                LastName = request.LastName,
                Address = request.Address,
                City = request.City,
                ZipCode = request.ZipCode,
                Country = request.Country,
                ShippingMethod = request.ShippingMethod,
                ShippingCost = request.ShippingCost,
                Status = OrderStatus.Draft
            };

            decimal subtotal = 0;
            foreach (var item in request.CartItems)
            {
                var product = await _context.Products.FindAsync(item.ProductId);
                if (product != null)
                {
                    var orderItem = new OrderItem
                    {
                        ProductId = product.Id,
                        Quantity = item.Quantity,
                        UnitPrice = product.Price
                    };
                    order.OrderItems.Add(orderItem);
                    subtotal += orderItem.TotalPrice;
                }
            }

            order.SubTotal = subtotal;
            order.TotalAmount = subtotal + order.ShippingCost;

            _context.Orders.Add(order);
            await _context.SaveChangesAsync();
            
            return MapOrderToResponse(order);
        }

        public async Task<OrderResponse?> ConfirmOrderAsync(int orderId)
        {
            var order = await _context.Orders
                .Include(o => o.OrderItems)
                .ThenInclude(oi => oi.Product)
                .FirstOrDefaultAsync(o => o.Id == orderId);
            
            if (order == null) return null;

            order.Status = OrderStatus.Pending;
            await _context.SaveChangesAsync();
            
            return MapOrderToResponse(order);
        }

        public async Task<IEnumerable<OrderResponse>> GetAllOrdersAsync()
        {
            var orders = await _context.Orders
                .Include(o => o.OrderItems)
                .ThenInclude(oi => oi.Product)
                .OrderByDescending(o => o.OrderDate)
                .ToListAsync();

            return orders.Select(MapOrderToResponse).ToList();
        }

        public async Task<OrderResponse?> GetOrderByIdAsync(int id)
        {
            var order = await _context.Orders
                .Include(o => o.OrderItems)
                .ThenInclude(oi => oi.Product)
                .FirstOrDefaultAsync(o => o.Id == id);

            return order == null ? null : MapOrderToResponse(order);
        }

        public async Task<OrderResponse> UpdateOrderStatusAsync(int orderId, OrderStatus status)
        {
            var order = await _context.Orders
                .Include(o => o.OrderItems)
                .ThenInclude(oi => oi.Product)
                .FirstOrDefaultAsync(o => o.Id == orderId);
            
            if (order != null)
            {
                order.Status = status;
                await _context.SaveChangesAsync();
            }
            
            return MapOrderToResponse(order!);
        }

        private OrderResponse MapOrderToResponse(Order order)
        {
            return new OrderResponse
            {
                Id = order.Id,
                OrderNumber = order.OrderNumber,
                Email = order.Email,
                FirstName = order.FirstName,
                LastName = order.LastName,
                Address = order.Address,
                City = order.City,
                ZipCode = order.ZipCode,
                Country = order.Country,
                ShippingMethod = order.ShippingMethod,
                ShippingCost = order.ShippingCost,
                OrderDate = order.OrderDate,
                Status = order.Status.ToString(),
                SubTotal = order.SubTotal,
                TotalAmount = order.TotalAmount,
                OrderItems = order.OrderItems.Select(oi => new OrderItemResponse
                {
                    Id = oi.Id,
                    ProductId = oi.ProductId,
                    ProductName = oi.Product?.Name ?? "Unknown Product",
                    Quantity = oi.Quantity,
                    UnitPrice = oi.UnitPrice,
                    TotalPrice = oi.TotalPrice
                }).ToList()
            };
        }

        private string GenerateOrderNumber()
        {
            return $"PET{DateTime.Now:yyyyMMdd}{Random.Shared.Next(1000, 9999)}";
        }
    }
}
