'use strict';

angular.module('profileEditor', ['ngRoute']);

angular.
module('profileEditor').
component('profileEditor', {
    templateUrl: '/profile/profile.editor/profile-editor.template.html',
    controller: ['$http', '$scope', '$routeParams', '$rootScope',
        function ProfileEditorController($http, $scope, $routeParams, $rootScope) {

            checkAuthority();

            $scope.errorMessage = '';
            $scope.successMessage = '';
            var photoBase64 = '';

            loadProfile();

            function loadProfile() {
                $http.get('/currentUser/profile').then(function(response) {
                    $scope.profile = response.data;
                    loadPhoto();
                });
            };

            $scope.save = function () {
                $scope.successMessage = '';
                $scope.errorMessage = '';
                validatePhoto(getPhoto())
            };

            function loadPhoto() {
                $scope.photo = 'data:image/jpeg;base64,' + $scope.profile.photo;
                photoBase64 = $scope.photo;
                isProductPhoto = true;
            };

            function validatePhoto(file) {
                if (file == undefined) {
                    if (photoBase64 == undefined || photoBase64 == '') {
                        $scope.errorMessage = 'Choose photo, please.';
                        return;
                    }
                    editInformation();
                    return;
                }
                encodeBase64(file);
            };

            function encodeBase64(file) {
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function () {
                    photoBase64 = reader.result;
                    editInformation();
                };
            };

            function editInformation() {
                $http({
                    method: 'PUT',
                    url: '/user/information/update',
                    data: {
                        id: $scope.profile.id,
                        firstName: $scope.profile.firstName,
                        lastName: $scope.profile.lastName,
                        email: $scope.profile.email,
                        lastVisit: $scope.profile.lastVisit,
                        photo: photoBase64
                    }
                }).then(function(response) {
                    if (response.status == 200) {
                        $scope.successMessage = 'Information saved successfully';
                    }
                },function errorCallback(response) {
                    $scope.errorMessage = 'Sorry, but an error occurred. Try again, please.';
                });
            };

            function getPhoto() {
                return document.getElementById('choose-photo-input').files[0];
            };

            document.getElementById('choose-photo-button').addEventListener("click", function() {
                document.getElementById('choose-photo-input').click();
            });

            function checkAuthority() {
                if (isAuthority('ROLE_ANONYMOUS')) {
                    window.location.replace('#!/hello');
                }
            };

            function isAuthority (role) {
                if ($rootScope.user == undefined) {
                    return false;
                }
                var authorities = $rootScope.user.authorities;
                for (var authority in authorities) {
                    if (authorities[authority].authority == role) {
                        return true;
                    }
                }
                return false;
            };

        }
    ]
});

var isProductPhoto = false;

function mouseOverEditProduct(element){
    element.childNodes[1].style.visibility='visible';
};

function mouseOutEditProduct(element){
    if (isProductPhoto) {
        element.childNodes[1].style.visibility='hidden';
    }
};

function readProductPhotoURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#photo').attr('src', e.target.result);
            isProductPhoto = true;
            document.getElementById('choose-photo-container').style.visibility='hidden';
        };

        reader.readAsDataURL(input.files[0]);
    }
};