<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Senarai Fasiliti E-Sukan</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4 text-center">Fasiliti Hub E-Sukan</h2>
        <div class="row">
            <c:forEach var="f" items="${facilities}">
                <div class="col-md-4 mb-4">
                    <div class="card h-100 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">${f.name}</h5>
                            <span class="badge bg-secondary mb-2">${f.type}</span>
                            <p class="card-text text-truncate">${f.description}</p>
                            <p class="fw-bold text-success">RM ${f.pricePerHour}/Jam</p>
                            <span class="badge ${f.status == 'Available' ? 'bg-success' : 'bg-danger'} mb-3">${f.status}</span>
                        </div>
                        <div class="card-footer bg-transparent border-top-0">
                            <a href="${pageContext.request.contextPath}/facility/detail?id=${f.id}" class="btn btn-primary w-100">Lihat Detail</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>