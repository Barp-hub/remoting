import pygame


class Ship:
    def __init__(self, screen):
        self.screen = screen
        self.image = pygame.image.load('image/ship.bmp')
        self.rect = self.image.get_rect()
        self.screen_rect = screen.get_rect()

        self.rect.centerx = self.screen_rect.centerx
        self.rect.bottom = self.screen_rect.bottom

        self.move_right = False
        self.move_left = False

    def update(self, setting):
        if self.move_right and self.rect.right < self.screen_rect.right:
            self.rect.centerx += setting.ship_speed_factor
        elif self.move_left and self.rect.left > 0:
            self.rect.centerx -= setting.ship_speed_factor

    def blitme(self):
        self.screen.blit(self.image, self.rect)
