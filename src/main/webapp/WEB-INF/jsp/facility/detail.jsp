<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Maklumat Fasiliti</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="card shadow max-width-600 mx-auto">
            <div class="card-header bg-dark text-white">
                <h3>${facility.name}</h3>
            </div>
            <div class="card-body">
                <p><strong>Kategori:</strong> <span class="badge bg-secondary">${facility.type}</span></p>
                <p><strong>Harga:</strong> RM ${facility.pricePerHour} / Jam</p>
                <p><strong>Status:</strong> <span class="badge ${facility.status == 'Available' ? 'bg-success' : 'bg-danger'}">${facility.status}</span></p>
                <hr>
                <h5>Deskripsi:</h5>
                <p>${facility.description}</p>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/facility/list" class="btn btn-secondary">Kembali</a>
                <button class="btn btn-success float-end" ${facility.status != 'Available' ? 'disabled' : ''}>Tempah Sekarang</button>
            </div>
        </div>
    </div>
</body>
</html>