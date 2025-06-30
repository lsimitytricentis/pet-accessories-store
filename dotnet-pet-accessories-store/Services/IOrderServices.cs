using PetAccessoriesStore.Models;
using PetAccessoriesStore.Models.DTOs;

namespace PetAccessoriesStore.Services
{
    public interface IOrderService
    {
        Task<OrderResponse> CreateDraftOrderAsync(CreateOrderRequest request);
        Task<OrderResponse?> ConfirmOrderAsync(int orderId);
        Task<IEnumerable<OrderResponse>> GetAllOrdersAsync();
        Task<OrderResponse?> GetOrderByIdAsync(int id);
        Task<OrderResponse> UpdateOrderStatusAsync(int orderId, OrderStatus status);
    }
}