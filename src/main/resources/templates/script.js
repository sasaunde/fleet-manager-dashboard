document.addEventListener('DOMContentLoaded', () => {
    // Handle car selection
    const carsTable = document.querySelector('.cars-table tbody');
    const carDetailsPanel = document.querySelector('.car-info-panel');
    
    // Add click event listener to table rows
    carsTable.addEventListener('click', (e) => {
        if (e.target.tagName === 'TD') {
            const row = e.target.closest('tr');
            const licensePlate = row.cells[0].textContent;
            
            // Update selected car details
            updateCarDetails(licensePlate);
            
            // Add visual feedback for selected row
            const rows = carsTable.querySelectorAll('tr');
            rows.forEach(row => row.classList.remove('selected'));
            row.classList.add('selected');
        }
    });

    // Function to update car details
    function updateCarDetails(licensePlate) {
        const carData = {
            'ABC123': {
                licensePlate: 'ABC123',
                make: 'Toyota',
                model: 'Camry',
                year: '2022',
                mileage: '12,500 km',
                fuelLevel: '25%',
                lastService: '2025-06-10',
                fuelStatus: 'Low',
                serviceDue: 'No',
                maintenance: 'None'
            },
            'XYZ789': {
                licensePlate: 'XYZ789',
                make: 'Honda',
                model: 'Civic',
                year: '2023',
                mileage: '5,800 km',
                fuelLevel: '75%',
                lastService: '2025-05-20',
                fuelStatus: 'Good',
                serviceDue: 'Yes',
                maintenance: 'Brakes'
            }
        };

        const car = carData[licensePlate];
        if (car) {
            // Update all detail items
            const detailItems = carDetailsPanel.querySelectorAll('.detail-item');
            detailItems[0].querySelector('.value').textContent = car.licensePlate;
            detailItems[1].querySelector('.value').textContent = car.make + ' ' + car.model + ' (' + car.year + ')';
            detailItems[2].querySelector('.value').textContent = car.mileage;
            detailItems[3].querySelector('.value').textContent = car.fuelLevel + ' (' + car.fuelStatus + ')';
            detailItems[4].querySelector('.value').textContent = car.lastService;
            detailItems[5].querySelector('.value').textContent = car.serviceDue;
            detailItems[6].querySelector('.value').textContent = car.maintenance;
        }
    }

    // Initialize with first car
    const firstCar = carsTable.querySelector('tr').cells[0].textContent;
    updateCarDetails(firstCar);

    // Add visual feedback for selected row
    carsTable.querySelectorAll('tr').forEach(row => {
        row.addEventListener('mouseover', () => {
            row.style.backgroundColor = '#f8f9fa';
        });
        row.addEventListener('mouseout', () => {
            row.style.backgroundColor = '';
        });
    });
});
